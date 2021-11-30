# Advent of Code 2015, using Kotlin

See <https://adventofcode.com/2015>, <https://kotlinlang.org/>.

## Getting Input

Grab your advent of code session cookie from the browser (the internet will help you)
and store it in an env var `AOC_SESSION`. Then `cd` into `./input` and run `./get_input <number of the day>`.

## Code Formatting

Code format is check by [ktlint](https://ktlint.github.io/)
via the Gradle plugin [Ktlint Gradle](https://github.com/jlleitschuh/ktlint-gradle)

Format the code using `./gradlw ktlintFormat`.

Adjust IDEA code style settings via `./gradlew ktlintApplyToIdea` and restart IDEA.

Add a Git pre-commit hook via `./gradlew addKtlintCheckGitPreCommitHook`.
