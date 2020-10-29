/*
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

import groovy.json.JsonSlurper

node ("master") {

  def slackChannel = params.CHANNEL
  def labels = params.LABELS

  def NodeHelper = library(identifier: 'openjdk-jenkins-helper@master').NodeHelper

  stage("checkLabels") {

      def label_list = labels.split("\n") 
      label_list.each { label ->
          if (!NodeHelper.nodeIsOnline(label)) {
              slackSend(channel: slackChannel, color: 'danger', message: 'WARNING: Node set offline: '+label)
          }
      }
  }
}

