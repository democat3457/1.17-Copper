package democat.copper.sound;

import java.util.ArrayList;

import democat.copper.CopperMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SoundEvents {
    public static ArrayList<SoundEvent> sounds = new ArrayList<SoundEvent>();

    public static final SoundEvent COPPER_BREAK = register("copper_break");
    public static final SoundEvent COPPER_STEP = register("copper_step");
    public static final SoundEvent COPPER_PLACE = register("copper_place");
    public static final SoundEvent COPPER_HIT = register("copper_hit");
    public static final SoundEvent COPPER_FALL = register("copper_fall");

    private static SoundEvent register(String name) {
        final ResourceLocation res = new ResourceLocation(CopperMod.MODID, name);
        SoundEvent sound = new SoundEvent(res);
        sound.setRegistryName(res);
        sounds.add(sound);
        return sound;
    }

    @SubscribeEvent
    public static void onRegistryEvent(RegistryEvent.Register<SoundEvent> event) {
        for (SoundEvent b : sounds) {
            event.getRegistry().register(b);
        }
    }
}