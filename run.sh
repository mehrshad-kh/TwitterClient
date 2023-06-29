#!/bin/zsh

mvn -DskipTests exec:java -Dexec.mainClass=com.mkh.twitter.client.TwitterApplication
