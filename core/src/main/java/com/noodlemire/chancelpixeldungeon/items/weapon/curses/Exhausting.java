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

package com.noodlemire.chancelpixeldungeon.items.weapon.curses;

import com.noodlemire.chancelpixeldungeon.Dungeon;
import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Buff;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Weakness;
import com.noodlemire.chancelpixeldungeon.items.weapon.Weapon;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Exhausting extends Weapon.Enchantment
{
	private static final ItemSprite.Glowing BLACK = new ItemSprite.Glowing(0x000000);

	@Override
	public boolean procChance(int level, Char attacker, Char defender, int damage)
	{
		return attacker == Dungeon.hero && Random.Int(15) == 0;
	}

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage)
	{
		if(doProc(weapon, attacker, defender, damage))
			Buff.affect(attacker, Weakness.class, Random.NormalIntRange(5, 20));

		return damage;
	}

	@Override
	public boolean curse()
	{
		return true;
	}

	@Override
	public ItemSprite.Glowing glowing()
	{
		return BLACK;
	}
}
