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

package com.noodlemire.chancelpixeldungeon.actors.buffs;

import com.noodlemire.chancelpixeldungeon.Dungeon;
import com.noodlemire.chancelpixeldungeon.items.Item;
import com.noodlemire.chancelpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.noodlemire.chancelpixeldungeon.ui.PinCushionIndicator;
import com.watabou.utils.Bundle;

import java.util.ArrayList;
import java.util.Collection;

public class PinCushion extends Buff
{
	{
		geyserCompatable = true;
	}

	private ArrayList<MissileWeapon> items = new ArrayList<>();

	public void stick(MissileWeapon projectile)
	{
		for(Item item : items)
		{
			if(item.isSimilar(projectile))
			{
				item.merge(projectile);
				return;
			}
		}
		items.add(projectile);
	}

	public MissileWeapon getMostRecent()
	{
		return items.get(items.size() - 1);
	}

	public MissileWeapon removeMostRecent()
	{
		MissileWeapon last = items.get(items.size() - 1);

		if(last.quantity() == 1)
			items.remove(items.size() - 1);
		else
			last = (MissileWeapon)last.split(1);

		if(items.size() == 0)
			detach();

		return last;
	}

	@Override
	public boolean act()
	{
		PinCushionIndicator.updateIcon();
		return super.act();
	}

	@Override
	public void detach()
	{
		for(Item item : items)
			Dungeon.level.drop(item, target.pos).sprite.drop();
		super.detach();
		PinCushionIndicator.updateIcon();
	}

	private static final String ITEMS = "items";

	@Override
	public void storeInBundle(Bundle bundle)
	{
		bundle.put(ITEMS, items);
		super.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle)
	{
		items = new ArrayList<>((Collection<MissileWeapon>) ((Collection<?>) bundle.getCollection(ITEMS)));
		super.restoreFromBundle(bundle);
	}
}