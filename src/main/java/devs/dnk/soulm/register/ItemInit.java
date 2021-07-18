package devs.dnk.soulm.register;

import devs.dnk.soulm.SoulMagica;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SoulMagica.MOD_ID);

    public static final RegistryObject<Item> TEST_SOUL = ITEMS.register("test_soul", () ->
            new Item(new Item.Properties().group(ItemGroup.COMBAT)));
}
