#!/usr/bin/env bash

# Stop on error
set -e

# Directory where downloaded JAR dependencies will be stored
libsDir="../lib"

# File that lists all dependency URLs (one per line)
depsFile="dependencies.txt"

scriptDir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Check if dependencies.txt exists, otherwise exit with error
if [[ ! -f "$depsFile" ]]; then
  echo "dependencies.txt not found" >&2
  exit 1
fi

# Ensure lib dir exists
mkdir -p "$scriptDir/$libsDir"

# Read each line (URL) from dependencies.txt
while IFS= read -r url || [[ -n "$url" ]]; do
  # Trim whitespace from the URL
  url="$(echo "$url" | xargs)"

  # Skip empty lines
  [[ -z "$url" ]] && continue

  # Extract the file name from the URL
  fileName="$(basename "$url")"
  
  # Combine the library directory and file name
  destination="$scriptDir/$libsDir/$fileName"

  # Check if the file already exists
  if [[ -f "$destination" ]]; then
    echo "Already exists: $fileName"
  else
    # Download the JAR if it does not exist
    echo "Downloading: $fileName"
    curl -L "$url" -o "$destination"
    echo "Downloaded: $fileName"
  fi
done < "$depsFile"

# Compile all Java source files in ../src recursively
# -cp ..\lib\* adds all JARs in the lib directory to the classpath
# -d ..\bin specifies the output directory for compiled .class files
mkdir -p "$scriptDir/../bin"
javac -cp "$scriptDir/../lib/*" \
  $(find "$scriptDir/../src" -name "*.java") \
  -d "$scriptDir/../bin"

# Package the compiled classes into a JAR
# -C ..\bin . means "change to bin directory and include all contents"
mkdir -p "$scriptDir/../target"
jar cf "$scriptDir/../target/duplex-rpc.jar" -C "$scriptDir/../bin" .

# Copy all JAR dependencies to the target directory
cp "$scriptDir/../lib/"*.jar "$scriptDir/../target/" 2>/dev/null || true