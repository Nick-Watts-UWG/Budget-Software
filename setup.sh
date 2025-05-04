#!/usr/bin/env bash

set -e

REPO_ROOT="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

BUDGET_DIR="$REPO_ROOT/Budget-Software/code"
SITE_DIR="$REPO_ROOT/XTremeBudgetSite"
PUBLIC_DIR="$SITE_DIR/public"

EXEC_SOURCE_NAME="Project.exe"
EXEC_PUBLISHED_NAME="ExtremeBudgetSoftwareNickWatts.exe"

function require_bin() {
  command -v "$1" >/dev/null 2>&1 || {
    echo "❌  '$1' is required but not installed. Aborting."
    exit 1
  }
}

echo "🔍  Checking prerequisites…"
require_bin mvn
require_bin node
require_bin npm
echo "✅  Maven, Node.js and npm are present."

echo
echo "🚧  Building Budget‑Software and running tests…"
cd "$BUDGET_DIR"

mvn -q clean package

echo "🚀  Starting the application in the background…"
APP_EXE="target/${EXEC_SOURCE_NAME}"
if [[ ! -f "$APP_EXE" ]]; then
  echo "❌  Cannot find '$APP_EXE'. Did the build fail? Aborting."
  exit 1
fi

"$APP_EXE" &
APP_PID=$!
sleep 10

echo "🧪  Executing unit tests…"
mvn -q test
echo "✅  Tests finished."

echo "🛑  Closing the running application…"
kill "$APP_PID" 2>/dev/null || true

echo
echo "📦  Copying executable to the Next.js site…"
mkdir -p "$PUBLIC_DIR"
cp -f "$APP_EXE" "$PUBLIC_DIR/$EXEC_PUBLISHED_NAME"
echo "✅  Copied $APP_EXE → $PUBLIC_DIR/$EXEC_PUBLISHED_NAME"

echo
echo "🌐  Installing site dependencies & starting dev server…"
cd "$SITE_DIR"
npm install
npm run dev &
WEB_PID=$!

echo
echo "🎉  Setup complete!"
echo "• App executable located at:   $PUBLIC_DIR/$EXEC_PUBLISHED_NAME"
echo "• Website running on:          http://localhost:3000"
echo "  (PID $WEB_PID – press Ctrl‑C to stop it)"
echo

wait $WEB_PID
