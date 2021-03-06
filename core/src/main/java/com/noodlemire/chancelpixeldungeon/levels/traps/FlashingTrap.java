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

package com.noodlemire.chancelpixeldungeon.levels.traps;

import com.noodlemire.chancelpixeldungeon.Assets;
import com.noodlemire.chancelpixeldungeon.Dungeon;
import com.noodlemire.chancelpixeldungeon.actors.Actor;
import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Bleeding;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Blindness;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Buff;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Cripple;
import com.noodlemire.chancelpixeldungeon.actors.mobs.Mob;
import com.noodlemire.chancelpixeldungeon.levels.Level;
import com.noodlemire.chancelpixeldungeon.levels.Terrain;
import com.noodlemire.chancelpixeldungeon.scenes.GameScene;

public class FlashingTrap extends Trap
{

	{
		color = GREY;
		shape = STARS;
	}

	@Override
	public void trigger()
	{
		Dungeon.playAt(Assets.SND_TRAP, pos);

		//this trap is not disarmed by being triggered
		reveal();
		Level.set(pos, Terrain.TRAP);
		activate();
	}

	@Override
	public void activate()
	{

		Char c = Actor.findChar(pos);

		if(c != null)
		{
			int damage = Math.max(0, (4 + Dungeon.depth) - c.drRoll());
			Buff.affect(c, Bleeding.class).set(damage);
			Buff.prolong(c, Blindness.class, 10f);
			Buff.prolong(c, Cripple.class, 20f);

			if(c instanceof Mob)
			{
				if(((Mob) c).state == ((Mob) c).HUNTING) ((Mob) c).state = ((Mob) c).WANDERING;
				((Mob) c).beckon(Dungeon.level.randomDestination());
			}
		}

		if(Dungeon.level.heroFOV[pos])
			GameScene.flash(0xFFFFFF);

		Dungeon.playAt(Assets.SND_BLAST, pos);
	}
}
