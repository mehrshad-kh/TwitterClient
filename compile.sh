#!/bin/zsh

mvn -DskipTests package exec:java -Dexec.mainClass=com.mkh.twitter.client.TwitterApplication
