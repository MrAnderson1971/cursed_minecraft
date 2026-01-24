package com.thomas.shampoo.event

import com.thomas.shampoo.ShampooMod
import com.thomas.shampoo.entity.Biden
import com.thomas.shampoo.entity.EntityInit
import com.thomas.shampoo.entity.Obama
import com.thomas.shampoo.entity.StevenArmstrong
import com.thomas.shampoo.network.PacketHandler
import net.minecraft.world.entity.SpawnPlacements
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.level.levelgen.Heightmap
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent

@EventBusSubscriber(modid = ShampooMod.MODID, bus = EventBusSubscriber.Bus.MOD)
object CommonModEvents {
  @SubscribeEvent
  fun entityAttributes(event: EntityAttributeCreationEvent) {
    event.put(EntityInit.BIDEN.get(), Biden.createAttributes().build())
    event.put(EntityInit.OBAMA.get(), Obama.createAttributes().build())
    event.put(EntityInit.STEVEN_ARMSTRONG.get(), StevenArmstrong.createAttributes().build())
  }

  @SubscribeEvent
  fun registerSpawnPlacements(event: SpawnPlacementRegisterEvent) {
    event.register(
        EntityInit.BIDEN.get(),
        SpawnPlacements.Type.ON_GROUND,
        Heightmap.Types.WORLD_SURFACE,
        { entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource ->
          Monster.checkMonsterSpawnRules(
              entityType,
              serverLevelAccessor,
              mobSpawnType,
              blockPos,
              randomSource,
          )
        },
        SpawnPlacementRegisterEvent.Operation.OR,
    )
    event.register(
        EntityInit.OBAMA.get(),
        SpawnPlacements.Type.ON_GROUND,
        Heightmap.Types.WORLD_SURFACE,
        { entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource ->
          Monster.checkMonsterSpawnRules(
              entityType,
              serverLevelAccessor,
              mobSpawnType,
              blockPos,
              randomSource,
          )
        },
        SpawnPlacementRegisterEvent.Operation.OR,
    )
    event.register<StevenArmstrong>(
        EntityInit.STEVEN_ARMSTRONG.get(),
        SpawnPlacements.Type.ON_GROUND,
        Heightmap.Types.WORLD_SURFACE,
        { entityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource ->
          Monster.checkMonsterSpawnRules(
              entityType,
              serverLevelAccessor,
              mobSpawnType,
              blockPos,
              randomSource,
          )
        },
        SpawnPlacementRegisterEvent.Operation.OR,
    )
  }

  @SubscribeEvent
  fun commonSetup(event: FMLCommonSetupEvent) {
    event.enqueueWork { PacketHandler.register() }
  }
}
