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
import com.noodlemire.chancelpixeldungeon.actors.blobs.Blob;
import com.noodlemire.chancelpixeldungeon.actors.blobs.Electricity;
import com.noodlemire.chancelpixeldungeon.scenes.GameScene;
import com.watabou.utils.PathFinder;

public class ShockingTrap extends Trap
{

	{
		color = YELLOW;
		shape = DOTS;
	}

	@Override
	public void activate()
	{
		Dungeon.playAt(Assets.SND_LIGHTNING, pos);

		for(int i : PathFinder.NEIGHBOURS9)
		{
			if(!Dungeon.level.solid[pos + i])
			{
				GameScene.add(Blob.seed(pos + i, 10, Electricity.class));
			}
		}
	}

}
