package me.andrz.maven.executable

import groovy.util.logging.Slf4j

/**
 *
 */
@Slf4j
public class MavenExecutableCli {

	public static void main(String[] args) {

        def cli = new CliBuilder()

        def cliOptions = cli.parse(args)
        log.debug "args: $cliOptions"

        def cliArgs = cliOptions.arguments()
        log.debug "args: $cliArgs"

        def artifact = cliArgs[0]
        def arguments = cliArgs.drop(1).join(' ')

        log.debug "artifact: $artifact"
        log.debug "arguments: $arguments"

        def out = new StringBuilder()
        def err = new StringBuilder()

        Process proc = run(artifact, arguments)

        proc.waitForProcessOutput(out, err)

        println out
        System.err.println err

        def exitValue = proc.exitValue()

        if (exitValue > 0) {
            System.exit(exitValue)
        }
	}

    public static Process run(String artifact, String arguments = null) {

        MavenExecutable mavenExecutable = new MavenExecutable()

        Process proc = mavenExecutable.run(artifact, new MavenExecutableParams(
                arguments: arguments
        ))

        return proc
    }

}
