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
import com.noodlemire.chancelpixeldungeon.actors.hero.Hero;
import com.noodlemire.chancelpixeldungeon.effects.CellEmitter;
import com.noodlemire.chancelpixeldungeon.effects.Speck;
import com.noodlemire.chancelpixeldungeon.items.Heap;
import com.noodlemire.chancelpixeldungeon.items.Item;
import com.noodlemire.chancelpixeldungeon.items.KindOfWeapon;
import com.noodlemire.chancelpixeldungeon.items.weapon.melee.Gloves;
import com.noodlemire.chancelpixeldungeon.messages.Messages;
import com.noodlemire.chancelpixeldungeon.scenes.GameScene;
import com.noodlemire.chancelpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;

public class DisarmingTrap extends Trap
{

	{
		color = RED;
		shape = LARGE_DOT;
	}

	@Override
	public void activate()
	{
		Heap heap = Dungeon.level.heaps.get(pos);

		if(heap != null)
		{
			int cell = Dungeon.level.randomRespawnCell();

			if(cell != -1)
			{
				Item item = heap.pickUp();
				Dungeon.level.drop(item, cell).seen = true;
				for(int i : PathFinder.NEIGHBOURS9)
					Dungeon.level.visited[cell + i] = true;
				GameScene.updateFog();

				Dungeon.playAt(Assets.SND_TELEPORT, pos);
				CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);
			}
		}

		if(Dungeon.hero.pos == pos)
		{
			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;

			if(weapon != null && !(weapon instanceof Gloves) && !weapon.cursed)
			{

				int cell = Dungeon.level.randomRespawnCell();
				if(cell != -1)
				{
					hero.belongings.weapon = null;
					Dungeon.quickslot.clearItem(weapon);
					weapon.updateQuickslot();

					Dungeon.level.drop(weapon, cell).seen = true;
					for(int i : PathFinder.NEIGHBOURS9)
						Dungeon.level.visited[cell + i] = true;
					GameScene.updateFog();

					GLog.w(Messages.get(this, "disarm"));

					Sample.INSTANCE.play(Assets.SND_TELEPORT);
					CellEmitter.get(pos).burst(Speck.factory(Speck.LIGHT), 4);
				}
			}
		}
	}
}
