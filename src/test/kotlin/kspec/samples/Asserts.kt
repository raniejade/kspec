package kspec.samples

import kspec.KSpec
import kspec.asserts.*

/**
 * @author Ranie Jade Ramiso
 */
class Asserts: KSpec() {
    override fun spec() {
        describe("assertions") {
            val foo: List<Int>? = listOf(1, 2)
            val bar: List<Int>? = listOf(1, 2)

            it("everything should pass") {

                expect({
                    expect(listOf(1, 2)).toBe(eq(listOf(2, 1), true))
                }).toBe(thrown(AssertionError::class))

                expect({
                    expect(listOf(1, 2)).toBe(notEq(listOf(2, 1)))
                }).toBe(thrown(AssertionError::class))

                expect({
                    expect(1).toBe(eq(1))
                    expect(1).toBe(notEq(2))
                    expect(null).toBeNull()
                    expect(foo).toBeNotNull()
                    expect(foo).toBe(same(foo))
                    expect(foo).toBe(notSame(bar))
                    expect(listOf(1, 2)).toBe(eq(listOf(2, 1)))

                    expect(listOf(1, 2)).toBe(notEq(listOf(2)))
                }).toBe(notThrown())
            }
        }
    }
}
