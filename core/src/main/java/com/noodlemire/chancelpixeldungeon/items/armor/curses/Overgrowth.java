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
import com.noodlemire.chancelpixeldungeon.effects.CellEmitter;
import com.noodlemire.chancelpixeldungeon.effects.particles.LeafParticle;
import com.noodlemire.chancelpixeldungeon.items.Generator;
import com.noodlemire.chancelpixeldungeon.items.armor.Armor;
import com.noodlemire.chancelpixeldungeon.plants.BlandfruitBush;
import com.noodlemire.chancelpixeldungeon.plants.Plant;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Overgrowth extends Armor.Glyph
{
	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing(0x000000);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage)
	{
		if(Random.Int(20) == 0)
		{
			Plant.Seed s;
			do
			{
				s = (Plant.Seed) Generator.random(Generator.Category.SEED);
			}
			while(s instanceof BlandfruitBush.Seed);

			Plant p = s.couch(defender.pos, null);

			//If this curse allowed warden benefits, it would be incredibly powerful
			p.activate(defender, false);
			CellEmitter.get(defender.pos).burst(LeafParticle.LEVEL_SPECIFIC, 10);
		}

		return damage;
	}

	@Override
	public ItemSprite.Glowing glowing()
	{
		return BLACK;
	}

	@Override
	public boolean curse()
	{
		return true;
	}
}
