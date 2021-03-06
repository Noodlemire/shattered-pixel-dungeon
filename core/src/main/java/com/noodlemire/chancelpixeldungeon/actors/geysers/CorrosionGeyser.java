package com.noodlemire.chancelpixeldungeon.actors.geysers;

import com.noodlemire.chancelpixeldungeon.actors.blobs.Blob;
import com.noodlemire.chancelpixeldungeon.actors.blobs.CorrosiveGas;

public class CorrosionGeyser extends ManualGeyser
{
	{
		spriteX = 1;
		spriteY = 2;
	}

	@Override
	public Class<? extends Blob> blobClass()
	{
		return CorrosiveGas.class;
	}
}
