package ir.amirroid.mafiauto.data.repository.player_role

import ir.amirroid.mafiauto.data.memory.PlayerMemoryHolder
import ir.amirroid.mafiauto.data.memory.PlayersWithRoleMemoryHelper
import ir.amirroid.mafiauto.data.memory.RoleMemoryHolder
import ir.amirroid.mafiauto.domain.model.player.PlayerWithRole
import ir.amirroid.mafiauto.domain.repository.PlayerRoleRepository

class PlayerRoleRepositoryImpl(
    private val roleMemoryHolder: RoleMemoryHolder,
    private val playerMemoryHolder: PlayerMemoryHolder,
    private val playersWithRoleMemoryHelper: PlayersWithRoleMemoryHelper
) : PlayerRoleRepository {
    override fun assignRoles(): List<PlayerWithRole> {
        val roles = roleMemoryHolder.value.shuffled()
        val players = playerMemoryHolder.value
        require(roles.size == players.size) { "Not enough roles for the number of players." }
        return players.mapIndexed { index, player ->
            PlayerWithRole(
                player = player,
                role = roles[index]
            )
        }
    }


    override fun savePlayersWithRoles(data: List<PlayerWithRole>) {
        playersWithRoleMemoryHelper.setValue(data)
    }

    override fun getAllSavedPlayersWithRoles(): List<PlayerWithRole> {
        return playersWithRoleMemoryHelper.value
    }
}