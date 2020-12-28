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

package com.noodlemire.chancelpixeldungeon.levels.rooms.special;

import com.noodlemire.chancelpixeldungeon.Dungeon;
import com.noodlemire.chancelpixeldungeon.items.Generator;
import com.noodlemire.chancelpixeldungeon.items.Item;
import com.noodlemire.chancelpixeldungeon.items.keys.IronKey;
import com.noodlemire.chancelpixeldungeon.items.scrolls.Scroll;
import com.noodlemire.chancelpixeldungeon.items.scrolls.ScrollOfCleansing;
import com.noodlemire.chancelpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.noodlemire.chancelpixeldungeon.levels.Level;
import com.noodlemire.chancelpixeldungeon.levels.Terrain;
import com.noodlemire.chancelpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Random;

public class LibraryRoom extends SpecialRoom
{
	public void paint(Level level)
	{
		Painter.fill(level, this, Terrain.WALL);
		Painter.fill(level, this, 1, Terrain.EMPTY_SP);

		Door entrance = entrance();

		Painter.fill(level, left + 1, top + 1, width() - 2, 1, Terrain.BOOKSHELF);
		Painter.drawInside(level, this, entrance, 1, Terrain.EMPTY_SP);

		int n = Random.IntRange(2, 3);
		for(int i = 0; i < n; i++)
		{
			int pos;
			do
			{
				pos = level.pointToCell(random());
			}
			while(level.map[pos] != Terrain.EMPTY_SP || level.heaps.get(pos) != null);
			Item item;
			if(i == 0)
				item = Random.Int(2) == 0 ? new ScrollOfIdentify() : new ScrollOfCleansing();
			else
				item = prize(level);
			level.drop(item, pos);
		}

		entrance.set(Door.Type.LOCKED);

		level.addItemToSpawn(new IronKey(Dungeon.depth));
	}

	private static Item prize(Level level)
	{

		Item prize = level.findPrizeItem(Scroll.class);
		if(prize == null)
			prize = Generator.random(Generator.Category.SCROLL);

		return prize;
	}
}
