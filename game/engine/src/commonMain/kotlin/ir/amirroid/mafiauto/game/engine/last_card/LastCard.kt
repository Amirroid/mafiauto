package ir.amirroid.mafiauto.game.engine.last_card

import ir.amirroid.mafiauto.game.engine.base.ActionsHandler
import ir.amirroid.mafiauto.game.engine.base.PlayerRoleTransformer
import ir.amirroid.mafiauto.game.engine.base.PlayerTransformer
import ir.amirroid.mafiauto.game.engine.models.Player
import org.jetbrains.compose.resources.StringResource

interface LastCardHandler : ActionsHandler

interface LastCard : PlayerTransformer, PlayerRoleTransformer {
    val name: StringResource
    val explanation: StringResource
    val key: String
    val targetCount: Int

    fun applyAction(
        player: Player,
        pickedPlayers: List<Player>,
        allPlayers: List<Player>,
        handler: LastCardHandler
    )
}