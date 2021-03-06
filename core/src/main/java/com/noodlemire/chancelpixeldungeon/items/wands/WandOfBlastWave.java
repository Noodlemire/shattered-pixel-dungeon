/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.noodlemire.chancelpixeldungeon.items.wands;

import com.noodlemire.chancelpixeldungeon.Assets;
import com.noodlemire.chancelpixeldungeon.Dungeon;
import com.noodlemire.chancelpixeldungeon.actors.Actor;
import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Paralysis;
import com.noodlemire.chancelpixeldungeon.effects.Effects;
import com.noodlemire.chancelpixeldungeon.effects.MagicMissile;
import com.noodlemire.chancelpixeldungeon.effects.Pushing;
import com.noodlemire.chancelpixeldungeon.items.weapon.melee.MagesStaff;
import com.noodlemire.chancelpixeldungeon.mechanics.Ballistica;
import com.noodlemire.chancelpixeldungeon.messages.Messages;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSpriteSheet;
import com.noodlemire.chancelpixeldungeon.tiles.DungeonTilemap;
import com.noodlemire.chancelpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.PointF;
import com.watabou.utils.Random;

public class WandOfBlastWave extends DamageWand
{
	{
		image = ItemSpriteSheet.WAND_BLAST_WAVE;

		collisionProperties = Ballistica.PROJECTILE;

		canCrit = true;
	}

	public int min(int lvl)
	{
		return 1 + lvl / 2;
	}

	public int max(int lvl)
	{
		return 5 + 3 * lvl;
	}

	@Override
	protected void onZap(Ballistica bolt)
	{
		Dungeon.playAt(Assets.SND_BLAST, bolt.collisionPos);
		BlastWave.blast(bolt.collisionPos);

		int damage = damageRoll();
		boolean critBoost = Dungeon.hero.critBoost(null);

		//presses all tiles in the AOE first
		for(int i : PathFinder.NEIGHBOURS9)
		{
			Dungeon.level.press(bolt.collisionPos + i, Actor.findChar(bolt.collisionPos + i), true);
		}

		//throws other chars around the center.
		for(int i : PathFinder.NEIGHBOURS8)
		{
			Char ch = Actor.findChar(bolt.collisionPos + i);

			if(ch != null)
			{
				processSoulMark(ch, chargesPerCast());
				ch.damage(Math.round(damage * 0.667f), this);

				if(ch.isAlive())
				{
					Ballistica trajectory = new Ballistica(ch.pos, ch.pos + i, Ballistica.MAGIC_BOLT);
					int strength = 1 + Math.round(level() / 2f);
					throwChar(ch, trajectory, strength, critBoost);
				}
			}
		}

		//throws the char at the center of the blast
		Char ch = Actor.findChar(bolt.collisionPos);
		if(ch != null)
		{
			processSoulMark(ch, chargesPerCast());
			ch.damage(damage, this);

			if(ch.isAlive() && bolt.path.size() > bolt.dist + 1)
			{
				Ballistica trajectory = new Ballistica(ch.pos, bolt.path.get(bolt.dist + 1), Ballistica.MAGIC_BOLT);
				int strength = level() + 3;
				throwChar(ch, trajectory, strength, critBoost);
			}
		}

		if(!curUser.isAlive())
		{
			Dungeon.fail(getClass());
			GLog.n(Messages.get(this, "ondeath"));
		}
	}

	public static void throwChar(final Char ch, final Ballistica trajectory, int power)
	{
		throwChar(ch, trajectory, power, false);
	}

	public static void throwChar(final Char ch, final Ballistica trajectory, int power, final boolean crit)
	{
		int dist = Math.min(trajectory.dist, power);

		if(ch.properties().contains(Char.Property.BOSS))
			dist /= 2;

		if(dist == 0 || ch.properties().contains(Char.Property.IMMOVABLE)) return;

		if(Actor.findChar(trajectory.path.get(dist)) != null)
		{
			dist--;
		}

		final int newPos = trajectory.path.get(dist);

		if(newPos == ch.pos) return;

		final int finalDist = dist;
		final int initialpos = ch.pos;

		Actor.addDelayed(new Pushing(ch, ch.pos, newPos, new Callback()
		{
			public void call()
			{
				if(initialpos != ch.pos)
				{
					//something caused movement before pushing resolved, cancel to be safe.
					ch.sprite.place(ch.pos);
					return;
				}
				ch.pos = newPos;
				if(ch.pos == trajectory.collisionPos)
				{
					int num = Random.NormalIntRange((finalDist + 1) / 2, finalDist);

					if(crit)
					{
						num *= 2;
						Wand.critFx(ch);
					}

					ch.damage(num, this);
					Paralysis.prolong(ch, Paralysis.class, num);
				}
				Dungeon.level.press(ch.pos, ch, true);
				if(ch == Dungeon.hero)
				{
					Dungeon.observe();
				}
			}
		}), -1);
	}

	@Override
	//behaves just like glyph of Repulsion
	public void onHit(MagesStaff staff, Char attacker, Char defender, int damage)
	{
		int level = Math.max(0, staff.level());

		// lvl 0 - 25%
		// lvl 1 - 40%
		// lvl 2 - 50%
		if(Random.Int(level + 4) >= 3)
		{
			int oppositeHero = defender.pos + (defender.pos - attacker.pos);
			Ballistica trajectory = new Ballistica(defender.pos, oppositeHero, Ballistica.MAGIC_BOLT);
			throwChar(defender, trajectory, 2);
		}
	}

	@Override
	protected void fx(Ballistica bolt, Callback callback)
	{
		MagicMissile.boltFromChar(curUser.sprite.parent,
				MagicMissile.FORCE,
				curUser.sprite,
				bolt.collisionPos,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public void staffFx(MagesStaff.StaffParticle particle)
	{
		particle.color(0x664422);
		particle.am = 0.6f;
		particle.setLifespan(3f);
		particle.speed.polar(Random.Float(PointF.PI2), 0.3f);
		particle.setSize(1f, 2f);
		particle.radiateXY(2.5f);
	}

	public static class BlastWave extends Image
	{
		private static final float TIME_TO_FADE = 0.2f;

		private float time;

		public BlastWave()
		{
			super(Effects.get(Effects.Type.RIPPLE));
			origin.set(width / 2, height / 2);
		}

		public void reset(int pos)
		{
			revive();

			x = (pos % Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - width) / 2;
			y = (pos / Dungeon.level.width()) * DungeonTilemap.SIZE + (DungeonTilemap.SIZE - height) / 2;

			time = TIME_TO_FADE;
		}

		@Override
		public void update()
		{
			super.update();

			if((time -= Game.elapsed) <= 0)
				kill();
			else
			{
				float p = time / TIME_TO_FADE;
				alpha(p);
				scale.y = scale.x = (1 - p) * 3;
			}
		}

		public static void blast(int pos)
		{
			Group parent = Dungeon.hero.sprite.parent;
			BlastWave b = (BlastWave) parent.recycle(BlastWave.class);
			parent.bringToFront(b);
			b.reset(pos);
		}
	}
}
