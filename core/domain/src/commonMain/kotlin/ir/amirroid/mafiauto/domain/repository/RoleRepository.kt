package ir.amirroid.mafiauto.domain.repository

import ir.amirroid.mafiauto.domain.model.role.RoleDescriptor

interface RoleRepository {
    fun getAllRoles(): List<RoleDescriptor>
    fun getAllSelectedRoles(): List<RoleDescriptor>
    fun selectRoles(newRoles: List<RoleDescriptor>)
    fun getRole(key: String): RoleDescriptor
}