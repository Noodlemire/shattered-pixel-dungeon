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

import com.noodlemire.chancelpixeldungeon.Badges;
import com.noodlemire.chancelpixeldungeon.Dungeon;
import com.noodlemire.chancelpixeldungeon.actors.Char;
import com.noodlemire.chancelpixeldungeon.actors.blobs.Blob;
import com.noodlemire.chancelpixeldungeon.actors.blobs.ToxicGas;
import com.noodlemire.chancelpixeldungeon.actors.hero.Hero;
import com.noodlemire.chancelpixeldungeon.effects.CellEmitter;
import com.noodlemire.chancelpixeldungeon.effects.particles.PoisonParticle;
import com.noodlemire.chancelpixeldungeon.messages.Messages;
import com.noodlemire.chancelpixeldungeon.ui.BuffIndicator;
import com.noodlemire.chancelpixeldungeon.utils.GLog;
import com.watabou.noosa.Image;

public class Poison extends DurationBuff implements Hero.Doom, Expulsion
{
	{
		type = buffType.NEGATIVE;
	}

	@Override
	public int icon()
	{
		return BuffIndicator.POISON;
	}

	@Override
	public void tintIcon(Image icon)
	{
		icon.hardlight(0.6f, 0.2f, 0.6f);
	}

	@Override
	public String toString()
	{
		return Messages.get(this, "name");
	}

	@Override
	public String heroMessage()
	{
		return Messages.get(this, "heromsg");
	}

	@Override
	public String desc()
	{
		return Messages.get(this, "desc", dispTurns(left()));
	}

	@Override
	public boolean attachTo(Char target)
	{
		if(super.attachTo(target) && target.sprite != null)
		{
			CellEmitter.center(target.pos).burst(PoisonParticle.SPLASH, 5);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean act()
	{
		if(target.isAlive())
		{
			target.damage((int) (left() / 3) + 1, this);

			spend(TICK);
			shorten(TICK);
		}
		else
			detach();

		return true;
	}

	@Override
	public Class<? extends Blob> expulse()
	{
		return ToxicGas.class;
	}

	@Override
	public void onDeath()
	{
		Badges.validateDeathFromPoison();

		Dungeon.fail(getClass());
		GLog.n(Messages.get(this, "ondeath"));
	}
}
