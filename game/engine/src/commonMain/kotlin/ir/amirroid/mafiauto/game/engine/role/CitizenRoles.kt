package ir.amirroid.mafiauto.game.engine.role

import ir.amirroid.mafiauto.game.engine.actions.role.*
import ir.amirroid.mafiauto.game.engine.base.ActionsHandler
import ir.amirroid.mafiauto.game.engine.models.InstantActionType
import ir.amirroid.mafiauto.game.engine.models.Player
import ir.amirroid.mafiauto.game.engine.models.StringResourcesMessage
import ir.amirroid.mafiauto.game.engine.utils.RoleKeys
import ir.amirroid.mafiauto.resources.Resources


data object Doctor : Role {
    override val key = RoleKeys.DOCTOR
    override val name = Resources.strings.doctor
    override val explanation = Resources.strings.doctorExplanation
    override val alignment = Alignment.Civilian
    override fun getNightAction() = DoctorSaveAction
    override fun getNightActionTargetPlayers(
        previewsTargets: List<Player>?,
        allPlayers: List<Player>
    ): List<Player> {
        val previewsTarget = previewsTargets?.firstOrNull()
        return allPlayers.filter { player -> player.id != previewsTarget?.id && player.isInGame && player.canBackWithSave }
    }
}

data object Detective : Role {
    override val key = RoleKeys.DETECTIVE
    override val name = Resources.strings.detective
    override val explanation = Resources.strings.detectiveExplanation
    override val alignment = Alignment.Civilian
    override fun getNightAction(): RoleAction? = null

    override val instantActionType: InstantActionType = InstantActionType.SHOW_ALIGNMENT
}

data class Civilian(val index: Int) : Role {
    override val key = "${RoleKeys.CIVILIAN}$index"
    override val name = Resources.strings.civilian
    override val explanation = Resources.strings.civilianExplanation
    override val alignment = Alignment.Civilian
    override fun hasNightAction(players: List<Player>): Boolean = false
    override fun getNightAction() = null
}

data object Sniper : Role {
    override val key = RoleKeys.SNIPER
    override val name = Resources.strings.sniper
    override val explanation = Resources.strings.sniperExplanation
    override val alignment = Alignment.Civilian
    override val isOptionalAbility: Boolean = true
    override val maxAbilityUses: Int = 2
    override val healthPoints: Int = 2
    override fun getNightAction() = ShootAction
}

data object Bulletproof : Role {
    override val key = RoleKeys.BULLETPROOF
    override val name = Resources.strings.bulletproof
    override val explanation = Resources.strings.bulletproofExplanation
    override val alignment = Alignment.Civilian
    override fun hasNightAction(players: List<Player>): Boolean = false
    override val healthPoints: Int = Int.MAX_VALUE
    override fun getNightAction() = null
}

data object Mayor : Role {
    override val key = RoleKeys.MAYOR
    override val name = Resources.strings.mayor
    override val explanation = Resources.strings.mayorExplanation
    override val alignment = Alignment.Civilian
    override fun getNightAction() = null
    override val isOptionalAbility: Boolean = true
    override fun getNightActionTargetPlayers(
        previewsTargets: List<Player>?,
        allPlayers: List<Player>
    ): List<Player> = emptyList()

    override fun getNightActionMessage(players: List<Player>): StringResourcesMessage {
        val shouldShowRoles = listOf(RoleKeys.DOCTOR, RoleKeys.SURGEON)
        val shouldShowPlayers = players.filter {
            it.role.key in shouldShowRoles
        }
        return StringResourcesMessage(
            Resources.strings.doctorDuoAnnouncement,
            listOf(
                shouldShowPlayers.getOrNull(0)?.name.orEmpty(),
                shouldShowPlayers.getOrNull(1)?.name.orEmpty()
            )
        )
    }
}


data object Silencer : Role {
    override val key = RoleKeys.SILENCER
    override val name = Resources.strings.silencer
    override val explanation = Resources.strings.silencerExplanation
    override val alignment = Alignment.Civilian
    override val maxAbilityUses: Int = 2
    override val isOptionalAbility: Boolean = true

    override fun getNightAction(): RoleAction = SilentAction
}


data object Bomber : Role {
    override val key = RoleKeys.BOMBER
    override val name = Resources.strings.bomber
    override val explanation = Resources.strings.bomberExplanation
    override val alignment = Alignment.Civilian
    override fun hasNightAction(players: List<Player>): Boolean = false
    override fun getNightAction() = null
    override fun onKillPlayer(players: List<Player>, handler: ActionsHandler) {
        val bomberIndex = players.indexOfFirst { it.role.key == key }
        val beforePlayers = players.take(bomberIndex)
        var currentPlayers = players
        beforePlayers.findLast { it.isAlive }?.let {
            currentPlayers = updatePlayer(currentPlayers, it.id) {
                copy(isAlive = false)
            }
        }
        val nextSize = if (beforePlayers.any { it.isInGame }) 1 else 2
        val nextPlayers = players.filterIndexed { index, _ -> index > bomberIndex }
        nextPlayers.filter { it.isInGame }.take(nextSize).forEach {
            currentPlayers = updatePlayer(currentPlayers, it.id) {
                copy(isAlive = false)
            }
        }
        handler.updatePlayers(currentPlayers)
    }
}

data object Gunsmith : Role {
    override val key = RoleKeys.GUNSMITH
    override val name = Resources.strings.gunsmith
    override val explanation = Resources.strings.gunsmithExplanation
    override val alignment = Alignment.Civilian
    override val maxAbilityUses: Int = 2
    override val isOptionalAbility: Boolean = true
    override val nightActionRequiredPicks: Int = 2

    override fun getNightAction(): RoleAction = GiveGunAction
    override fun getNightActionMessage(players: List<Player>): StringResourcesMessage? {
        return StringResourcesMessage(Resources.strings.gunsmithInfo)
    }
}

data object Ranger : Role {
    override val key = RoleKeys.RANGER
    override val name = Resources.strings.ranger
    override val explanation = Resources.strings.rangerExplanation
    override val alignment = Alignment.Civilian
    override val triggersWhenTargetedBy: List<String>
        get() = listOf(RoleKeys.GOD_FATHER)

    override fun getNightAction(): RoleAction = RangerReactiveAction
}