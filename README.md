# clojure-raw-rest-api

Simple REST API in Clojure created as a Cloud Run example app

## Usage

    - `/` - basic hello-world
    - '/status' - health check
    - `/env` - environment variable from OS

## Testing

    ```bash
    lein test
    ```

## Build

    ```bash
    lein uberjar
    ```

## License

Copyright © 2020 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
