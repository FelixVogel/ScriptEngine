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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;

/**
 * This class represents the basic implementation of a underlying {@link ScriptEngine}
 *
 * @author Felix Vogel
 */
public abstract class Engine {

    protected final File file;
    protected final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");

    private long lastLoaded;

    /**
     * Create a new {@link Engine} for the defined script file
     *
     * @param file The script file
     */
    public Engine(final File file) {
        this.file = file;

        load();
    }

    /**
     * Called when the script is to be loaded, refreshes the last load time
     */
    public void load() {
        this.lastLoaded = file.lastModified();

        onLoad();
    }

    /**
     * Called when the script is first loaded or reloaded
     */
    protected abstract void onLoad();

    /**
     * When this method is called, <b>IF</b> the underlying file was modified, the script will be reloaded
     * by a {@link Engine#load()} call
     */
    public final void reload() {
        if (file.lastModified() != lastLoaded) {
            EngineUtility.debug("Reloading Script: " + file.getAbsolutePath());
            load();
        }
    }

    /**
     * Get the underlying {@link ScriptEngine}
     *
     * @return The underlying {@link ScriptEngine}
     */
    public final ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

}
