package ftclib.kotlin.extensions.geometry

import ftclib.geometry.Rotation2d
import ftclib.geometry.Translation2d

/**
 * @author Jaran Chao
 *
 * Add quality of life update to be able to call rotateBy in infix notation
 */
infix fun Translation2d.rotateBy(other: Rotation2d): Translation2d =
        this.rotateBy(other)
