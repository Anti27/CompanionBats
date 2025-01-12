package dev.fulmineo.companion_bats.data;

import dev.fulmineo.companion_bats.CompanionBats;
import dev.fulmineo.companion_bats.item.CompanionBatAccessoryItem;
import draylar.staticcontent.api.ContentData;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CompanionBatAccessory implements ContentData {
	public String name;
	public CompanionBatAbility ability;

	@Override
	public void register(Identifier fileID) {
		CompanionBatAccessoryItem item = new CompanionBatAccessoryItem(this.name, this.ability, new FabricItemSettings().group(CompanionBats.GROUP).maxCount(1));
		Registry.register(Registry.ITEM, new Identifier(CompanionBats.MOD_ID, this.name), item);
	}
}

