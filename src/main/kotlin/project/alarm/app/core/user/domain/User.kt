package project.alarm.app.core.user.domain

import jakarta.persistence.*
import java.lang.Boolean.FALSE

@Entity
class User(
    @Column(name = "name")
    private var _name :String,

    @Column(name = "alarm")
    private var _alarm :Boolean = FALSE
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    val name:String
        get() = _name

    val alarm:Boolean
        get() = _alarm

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }
        other as User
        if (id != other.id) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
