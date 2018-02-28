package org.codice.compliance

/**
 * Copyright (c) Codice Foundation
 *
 * <p>This is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version.
 *
 * <p>This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details. A copy of the GNU Lesser General Public
 * License is distributed along with this program and can be found at
 * <http://www.gnu.org/licenses/lgpl.html>.
 */
import org.w3c.dom.Node
import java.util.*

class SAMLComplianceException private constructor(message: String) : Exception(message) {
    companion object {
        private val BUNDLE = ResourceBundle.getBundle("ExceptionCodes")!!

        fun create(vararg codes: String): SAMLComplianceException {
            val msg = codes.map(Companion::readCode)
                    .fold("Errors:\n") { acc, s ->
                        "$acc\n$s"
                    }
            return SAMLComplianceException(msg)
        }

        fun createWithReqMessage(section: String, attribute: String, parent: String): SAMLComplianceException {
            return SAMLComplianceException(String.format("%s=%s is required in %s.", section, attribute, parent))
        }

        private fun readCode(code: String): String {
            return "${trimUnderscore(code)}: ${BUNDLE.getString(code)}"
        }

        private fun trimUnderscore(codeValue: String): String {
            val underscoreIndex = codeValue.indexOf("_")

            return if (underscoreIndex == -1) codeValue
            else codeValue.substring(0, underscoreIndex)
        }
    }
}

/** Extensions to Node class **/

/**
 * Finds a Node's child by its name.
 *
 * @param name - Name of Assertions.children
 * @return list of Assertions.children matching the name provided
 */
fun Node.children(name: String): List<Node> {
    val childNodes = mutableListOf<Node>()
    var i = this.childNodes.length - 1
    while (i >= 0) {
        val child = this.childNodes.item(i)
        if (child.localName == name)
            childNodes.add(child); i -= 1
    }
    return childNodes
}

/**
 * Finds a Node's child by its name.
 *
 * @param name - Name of Assertions.children
 * @return list of Assertions.children matching the name provided
 */
fun Node.allChildren(name: String): List<Node> {
    val nodes = mutableListOf<Node>()
    var i = this.childNodes.length - 1
    while (i >= 0) {
        val child = this.childNodes.item(i)
        if (child.localName == name)
            nodes.add(child)
        nodes.addAll(child.allChildren(name)); i -= 1
    }
    return nodes
}