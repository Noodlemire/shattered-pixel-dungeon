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

package com.noodlemire.chancelpixeldungeon.items.potions;

import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Bleeding;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Buff;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Cripple;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Healing;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Poison;
import com.noodlemire.chancelpixeldungeon.actors.buffs.Weakness;
import com.noodlemire.chancelpixeldungeon.actors.hero.Hero;
import com.noodlemire.chancelpixeldungeon.messages.Messages;
import com.noodlemire.chancelpixeldungeon.sprites.ItemSpriteSheet;
import com.noodlemire.chancelpixeldungeon.utils.GLog;

public class PotionOfHealing extends Potion
{
	{
		icon = ItemSpriteSheet.Icons.POTION_HEALING;

		bones = true;
	}

	@Override
	public void apply(Hero hero)
	{
		//starts out healing 30 hp, equalizes with hero health total at level 11
		Buff.affect(hero, Healing.class).setHeal((int) (0.8f * hero.HT() + 14), 0.333f, 0);
		cure(hero);
		GLog.p(Messages.get(this, "heal"));
	}

	public static void cure(Char ch)
	{
		Buff.detach(ch, Poison.class);
		Buff.detach(ch, Cripple.class);
		Buff.detach(ch, Weakness.class);
		Buff.detach(ch, Bleeding.class);

	}

	@Override
	public int price()
	{
		return isKnown() ? 30 * quantity : super.price();
	}
}
