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

package com.noodlemire.chancelpixeldungeon.items.weapon.missiles;

import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Bleeding;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Buff;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSpriteSheet;

public class Tomahawk extends MissileWeapon
{
	{
		image = ItemSpriteSheet.TOMAHAWK;
		tier = 4;
		baseUses = 5;
	}

	@Override
	public int min(int lvl)
	{
		return Math.round(1.5f * tier) +   //6 base, down from 8
		       2 * lvl;                    //scaling unchanged
	}

	@Override
	public int max(int lvl)
	{
		return Math.round(3.75f * tier) +  //15 base, down from 20
		       (tier - 2) * lvl;               //+2 per level, down from +4
	}

	@Override
	public int crit(Char attacker, Char defender, int damage)
	{
		Buff.affect(defender, Bleeding.class).set(damage);
		return damage;
	}
}
