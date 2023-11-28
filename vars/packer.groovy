// vars/packer.groovy

// Build Packer artifact
void build(Map config) {
    // Input checking
    assert config.template instanceof String : 'The required template parameter was not set.'
    assert fileExists(config.template) : "The template file or templates directory ${config.template} does not exist!"
    config.bin = config.bin ?: 'packer'

    String cmd = "${config.bin} build -color=false"

    // Check for optional inputs
    if (config.varFile) {
        assert fileExists(config.varFile) : "The var file ${config.varFile} does not exist!"
        cmd += " -var-file=${config.varFile}"
    }
    if (config.var) {
        assert (config.var instanceof Map) : 'The var parameter must be a Map.'

        config.var.each() { var, value ->
            // Convert value to JSON if not a string type
            if (value instanceof List || value instanceof Map) {
                value = writeJSON(json: value, returnText: true)
            }

            cmd += " -var ${var}=${value}"
        }
    }
    if (config.only) {
        assert (config.only instanceof List) : 'The only parameter must be a list of strings.'
        cmd += " -only=${config.only.join(',')}"
    }
    if (config.force == true) {
        cmd += " -force"
    }
    if (config.onError) {
        assert (['default', 'abort', 'ask', 'run-cleanup-provisioner'].contains(config.onError)) : "The argument must be one of: default, abort, ask, or run-cleanup-provisioner."
        cmd += " -on-error=${config.onError}"
    }

    // Create artifact with Packer
    try {
        if (config.template ==~ /\.pkr\./) {
            sh(label: 'Packer Build', script: "${cmd} ${config.template}")
        } else {
            dir(config.template) {
                sh(label: 'Packer Build', script: "${cmd} .")
            }
        }
    } catch(Exception error) {
        print 'Failure using Packer build.'
        throw error
    }
    print 'Packer build artifact created successfully.'
}

// Format Packer configuration
void fmt(Map config) {
    // Input checking
    assert config.template instanceof String : 'The required template parameter was not set.'
    assert fileExists(config.template) : "The template file or templates directory ${config.template} does not exist!"
    if (config.write && config.check) {
        throw new Exception("The 'write' and 'check' options for packer.fmt are mutually exclusive - only one can be enabled.")
    }
    config.bin = config.bin ?: 'packer'

    String cmd = "${config.bin} fmt"

    // Check for optional inputs
    if (config.diff == true) {
        cmd += ' -diff'
    }
    if (config.check == true) {
        cmd += ' -check'
    } else if (config.write == true) {
        cmd += ' -write'
    }

    try {
        if (config.template ==~ /\.pkr\./) {
            final int fmtStatus = sh(label: 'Packer Format', returnStatus: true, script: "${cmd} ${config.template}")
        } else {
            dir(config.template) {
                final int fmtStatus = sh(label: 'Packer Format', returnStatus: true, script: "${cmd} .")
            }
        }

        // Report if formatting check detected issues
        if ((config.check == true) && (fmtStatus != 0)) {
            print 'Packer fmt has detected formatting errors.'
        }
    } catch(Exception error) {
        print 'Failure using Packer fmt.'
        throw error
    }
    print 'Packer fmt was successful.'
}

// Initialize Packer configuration
void init(Map config) {
    // Input checking
    assert fileExists(config.dir) : "Working template directory ${config.dir} does not exist."
    config.bin = config.bin ?: 'packer'

    String cmd = "${config.bin} init"

    // Check for optional inputs
    if (config.upgrade == true) {
        cmd += ' -upgrade'
    }

    // Initialize the working template directory
    try {
        dir(config.dir) {
            sh(label: 'Packer Init', script: "${cmd} .")
        }
    } catch(Exception error) {
        print 'Failure using Packer init.'
        throw error
    }
    print 'Packer init was successful.'
}

// Inspect Packer template
void inspect(String template, String bin = '/usr/bin/packer') {
    // Input checking
    assert fileExists(template) : "A file does not exist at ${template}."

    // Inspect the Packer template
    try {
        sh(label: 'Packer Inspect', script: "${bin} inspect ${template}")
    } catch(Exception error) {
        print 'Failure inspecting the template.'
        throw error
    }
    print 'Packer inspect was successful.'
}

// Install Packer
void install(Map config) {
    // Input checking
    config.installPath = config.installPath ? config.installPath : '/usr/bin'
    assert (config.platform instanceof String && config.version instanceof String) : 'A required parameter ("platform" or "version") is missing from the packer.install method. Please consult the documentation for proper usage.'

    new utils().makeDirParents(config.installPath)

