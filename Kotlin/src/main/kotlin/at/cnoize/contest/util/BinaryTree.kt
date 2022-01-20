package at.cnoize.contest.util

open class BinaryTreeNode<Element>(
    val value: Element,
    var leftChild: BinaryTreeNode<Element>? = null,
    var rightChild: BinaryTreeNode<Element>? = null
) {

    fun preorderTraversal(action: (Element) -> Unit) {
        action(value)
        leftChild?.preorderTraversal(action)
        rightChild?.preorderTraversal(action)
    }

    fun postoderTraversal(action: (Element) -> Unit) {
        leftChild?.postoderTraversal(action)
        rightChild?.postoderTraversal(action)
        action(value)
    }

    fun inoderTraversal(action: (Element) -> Unit) {
        leftChild?.inoderTraversal(action)
        action(value)
        rightChild?.inoderTraversal(action)
    }
}



class BinaryTree<Element>(val rootNode: BinaryTreeNode<Element>) {
}
