First of all, commit all pending changes.

Then run this command:

git rm -r --cached .
This removes everything from the index, then just run:

git add .
Commit it:

git commit -m ".gitignore is now working"