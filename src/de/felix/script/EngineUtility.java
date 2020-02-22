/**
 * MIT License
 *
 * Copyright (c) 2020 Felix Vogel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.felix.script;

/**
 * @author Felix Vogel
 */
public final class EngineUtility {

    private static final EngineUtility I = new EngineUtility();

    public static void setDebug(final boolean debug) {
        I.debug = debug;
    }

    public static void debug(final String message) {
        I.debug0(message);
    }

    public static void debug(final Throwable throwable) {
        I.debug0(throwable);
    }

    // Class

    private boolean debug = false;

    private EngineUtility() {}

    public void debug0(final String message) {
        if (debug) System.err.println(message);
    }

    public void debug0(final Throwable e) {
        if (debug) e.printStackTrace();
    }

}
