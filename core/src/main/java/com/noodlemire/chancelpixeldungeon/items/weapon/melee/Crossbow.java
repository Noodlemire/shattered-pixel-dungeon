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

package com.noodlemire.chancelpixeldungeon.items.weapon.melee;

import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSpriteSheet;

public class Crossbow extends MeleeWeapon
{
	{
		//check Dart.class for additional properties
		image = ItemSpriteSheet.CROSSBOW;
		tier = 4;
	}

	@Override
	public int max(int lvl)
	{
		return Math.round(super.max(lvl) * 0.8f); //20 base, down from 25, and +4 per level, down from +5
	}

	@Override
	public int crit(Char attacker, Char defender, int damage)
	{
		//Does nothing upon a melee crit; See Dart.java
		return damage;
	}
}
