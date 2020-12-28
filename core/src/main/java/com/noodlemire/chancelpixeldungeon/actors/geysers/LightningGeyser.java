package com.noodlemire.chancelpixeldungeon.actors.geysers;

import com.noodlemire.chancelpixeldungeon.actors.blobs.Blob;
import com.noodlemire.chancelpixeldungeon.actors.blobs.ThunderCloud;

public class LightningGeyser extends AutoGeyser
{
	{
		spriteX = 3;
		spriteY = 1;
	}

	@Override
	public Class<? extends Blob> blobClass()
	{
		return ThunderCloud.class;
	}
}
