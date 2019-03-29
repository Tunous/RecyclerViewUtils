package me.thanel.recyclerviewutils.adapter

/**
 * Creates a new instance of the [Lazy] that uses the specified initialization function
 * [initializer] to create and initialize an instance of [MultiTypeAdapterWrapper].
 *
 * Sample usage:
 *
 *      val adapterWrapper by lazyAdapterWrapper {
 *          register(FirstItemViewBinder())
 *          register(SecondItemViewBinder(), SecondItemCallback())
 *          ...
 *      }
 */
fun lazyAdapterWrapper(initializer: MultiTypeAdapterWrapper.() -> Unit) = lazy {
    MultiTypeAdapterWrapper().apply(initializer)
}
