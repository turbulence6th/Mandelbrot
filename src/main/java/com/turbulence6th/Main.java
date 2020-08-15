/**
 * This product currently only contains code developed by authors
 * of specific components, as identified by the source code files.
 *
 * Since product implements StAX API, it has dependencies to StAX API
 * classes.
 *
 * For additional credits (generally to people who reported problems)
 * see CREDITS file.
 */
package com.turbulence6th;

import com.aparapi.Kernel;
import com.aparapi.Range;

public class Main {

    public static void main(String[] args) {
        new FractalFrame(500, 500);
    }
}
