# Directory where downloaded JAR dependencies will be stored
$libsDir = "..\lib"

# File that lists all dependency URLs (one per line)
$depsFile = "dependencies.txt"

# Check if dependencies.txt exists, otherwise exit with error
if (-not (Test-Path $depsFile)) {
    Write-Error "dependencies.txt not found"
    exit 1
}

# Read each line (URL) from dependencies.txt
Get-Content $depsFile | ForEach-Object {
    # Trim whitespace from the URL
    $url = $_.Trim()

    # Skip empty lines
    if ([string]::IsNullOrWhiteSpace($url)) {
        return
    }

    try {
        # Extract the file name from the URL
        $fileName = [System.IO.Path]::GetFileName($url)

        # Combine the library directory and file name
        $destination = Join-Path $libsDir $fileName

        # Resolve full absolute path relative to the script's location
        $destination = [System.IO.Path]::GetFullPath(
            [System.IO.Path]::Combine($PSScriptRoot, $destination)
        )

        # Check if the file already exists
        if (Test-Path $destination -PathType Leaf) {
            Write-Host "Already exists: $fileName"
        } else {
            # Download the JAR if it does not exist
            Write-Host "Downloading: $fileName"
            Invoke-WebRequest -Uri $url -OutFile $destination
            Write-Host "Downloaded: $fileName"
        }
    }
    catch {
        # Print warning if any error occurs during processing
        Write-Warning "Failed to process URL: $url"
        Write-Warning $_
    }
}

# Compile all Java source files in ../src recursively
# -cp ..\lib\* adds all JARs in the lib directory to the classpath
# -d ..\bin specifies the output directory for compiled .class files
javac -cp ..\lib\* (Get-ChildItem ..\src -Recurse -Filter *.java | ForEach-Object FullName) -d ..\bin

# Package the compiled classes into a JAR
# -C ..\bin . means "change to bin directory and include all contents"
jar cf ..\target\duplex-rpc.jar -C ..\bin .

# Copy all JAR dependencies to the target directory
copy ..\lib\*.jar ..\target