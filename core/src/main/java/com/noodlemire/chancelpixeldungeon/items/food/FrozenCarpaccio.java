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

package com.noodlemire.chancelpixeldungeon.items.food;

import com.noodlemire.chancelpixeldungeon.actors.buffs.Barkskin;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Bleeding;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Buff;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Cripple;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Drowsy;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Hunger;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Invisibility;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Poison;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Slow;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Vertigo;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Weakness;
import com.noodlemire.chancelpixeldungeon.actors.hero.Hero;
import com.noodlemire.chancelpixeldungeon.effects.Speck;
import com.noodlemire.chancelpixeldungeon.messages.Messages;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSpriteSheet;
import com.noodlemire.chancelpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class FrozenCarpaccio extends Food
{
	{
		image = ItemSpriteSheet.CARPACCIO;
		energy = Hunger.HUNGRY / 2f;
	}

	@Override
	public void execute(Hero hero, String action)
	{
		super.execute(hero, action);

		if(action.equals(AC_EAT))
		{
			effect(hero);
		}
	}

	public int price()
	{
		return 10 * quantity;
	}

	public static void effect(Hero hero)
	{
		switch(Random.Int(5))
		{
			case 0:
				GLog.i(Messages.get(FrozenCarpaccio.class, "invis"));
				Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
				break;
			case 1:
				GLog.i(Messages.get(FrozenCarpaccio.class, "hard"));
				Buff.affect(hero, Barkskin.class).set(hero.HT() / 4f);
				break;
			case 2:
				GLog.i(Messages.get(FrozenCarpaccio.class, "refresh"));
				Buff.detach(hero, Poison.class);
				Buff.detach(hero, Cripple.class);
				Buff.detach(hero, Weakness.class);
				Buff.detach(hero, Bleeding.class);
				Buff.detach(hero, Drowsy.class);
				Buff.detach(hero, Slow.class);
				Buff.detach(hero, Vertigo.class);
				break;
			case 3:
				GLog.i(Messages.get(FrozenCarpaccio.class, "better"));
				if(hero.HP() < hero.HT())
				{
					hero.heal(hero.HT() / 4, curItem);
					hero.sprite.emitter().burst(Speck.factory(Speck.HEALING), 1);
				}
				break;
		}
	}

	public static Food cook(MysteryMeat ingredient)
	{
		FrozenCarpaccio result = new FrozenCarpaccio();
		result.quantity = ingredient.quantity();
		return result;
	}
}
