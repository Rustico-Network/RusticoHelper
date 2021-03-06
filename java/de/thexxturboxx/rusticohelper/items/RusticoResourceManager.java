package de.thexxturboxx.rusticohelper.items;

import java.lang.reflect.Field;
import java.util.*;

import de.thexxturboxx.rusticohelper.RusticoHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RusticoResourceManager implements IResourceManagerReloadListener {
	
	public String itemName;
	public Item toOverride;
	public int meta;
	
	public RusticoResourceManager(String itemName, Item toOverride, int meta) {
		this.itemName = itemName;
		this.toOverride = toOverride;
		this.meta = meta;
	}
	
	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		ItemOverrideList i = Minecraft.getMinecraft()
				.getRenderItem().getItemModelMesher().getItemModel(new ItemStack(toOverride, 1, meta)).getOverrides();
		Map<ResourceLocation, Float> m = new HashMap<ResourceLocation, Float>();
		m.put(new ResourceLocation(RusticoHelper.ID, itemName), 1F);
		try {
			Field d = ItemOverrideList.class.getDeclaredField("overrides");
			d.setAccessible(true);
			List<ItemOverride> l = (List<ItemOverride>) d.get(i);
			l.add(new ItemOverride(new ModelResourceLocation(RusticoHelper.ID + ":" + itemName, "inventory"), m));
			d.set(i, l);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}