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

package com.noodlemire.chancelpixeldungeon.items.weapon.enchantments;

import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Buff;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Paralysis;
import com.noodlemire.chancelpixeldungeon.effects.Speck;
import com.noodlemire.chancelpixeldungeon.items.weapon.Weapon;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSprite;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Stunning extends Weapon.Enchantment
{
	private static final ItemSprite.Glowing YELLOW = new ItemSprite.Glowing(0xCCAA44);

	@Override
	public boolean procChance(int level, Char attacker, Char defender, int damage)
	{
		// lvl 0 - 13%
		// lvl 1 - 22%
		// lvl 2 - 30%
		return Random.Int(level + 8) >= 7;
	}

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage)
	{
		int level = Math.max(0, weapon.level());

		if(doProc(weapon, attacker, defender, damage))
		{
			Buff.prolong(defender, Paralysis.class, Random.Float(1, 1.5f + level));
			defender.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12);
		}

		return damage;
	}

	@Override
	public Glowing glowing()
	{
		return YELLOW;
	}
}
