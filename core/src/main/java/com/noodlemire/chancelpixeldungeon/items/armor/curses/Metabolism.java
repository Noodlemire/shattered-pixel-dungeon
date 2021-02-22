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

package com.noodlemire.chancelpixeldungeon.items.armor.curses;

import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Hunger;
import com.noodlemire.chancelpixeldungeon.effects.Speck;
import com.noodlemire.chancelpixeldungeon.items.armor.Armor;
import com.noodlemire.chancelpixeldungeon.items.armor.Armor.Glyph;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSprite;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSprite.Glowing;
import com.noodlemire.chancelpixeldungeon.ui.BuffIndicator;
import com.watabou.utils.Random;

public class Metabolism extends Glyph
{

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing(0x000000);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage)
	{
		if(Random.Int(6) == 0)
		{
			//assumes using up 10% of starving, and healing of 1 hp per 10 turns;
			int healing = Math.min((int) Hunger.STARVING / 100, defender.HT() - defender.HP());

			if(healing > 0)
			{
				Hunger hunger = defender.buff(Hunger.class);

				if(hunger != null && !hunger.isStarving())
				{
					hunger.reduceHunger(healing * -10);
					BuffIndicator.refreshHero();

					defender.heal(healing, this);
					defender.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
				}
			}

		}

		return damage;
	}

	@Override
	public Glowing glowing()
	{
		return BLACK;
	}

	@Override
	public boolean curse()
	{
		return true;
	}
}
