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

package com.noodlemire.chancelpixeldungeon.actors.mobs;

import com.noodlemire.chancelpixeldungeon.Dungeon;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Buff;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Chill;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Frost;
import com.noodlemire.chancelpixeldungeon.items.quest.Embers;
import com.noodlemire.chancelpixeldungeon.sprites.NewbornElementalSprite;

public class NewbornElemental extends Elemental
{
	{
		spriteClass = NewbornElementalSprite.class;

		EXP = 8;

		setHT(40, true);

		properties.add(Property.MINIBOSS);
	}

	@Override
	public void add(Buff buff)
	{
		if(buff instanceof Frost || buff instanceof Chill)
		{
			die(buff);
		}
		else
		{
			super.add(buff);
		}
	}

	@Override
	public void die(Object cause)
	{
		super.die(cause);
		Dungeon.level.drop(new Embers(), pos).sprite.drop();
	}
}
