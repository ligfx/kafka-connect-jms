/*
 * Copyright 2018 Macronova.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.macronova.kafka.connect.jms.util;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Queue;
import jakarta.jms.Topic;
import com.github.marschall.jakartajmsadapter.JakartaConnectionFactory;
import com.github.marschall.jakartajmsadapter.Wrapper;

public abstract class JmsUtils {
	public static ConnectionFactory asJakartaConnectionFactory(Object instance) {
		if (instance instanceof javax.jms.ConnectionFactory) {
			return new com.github.marschall.jakartajmsadapter.JakartaConnectionFactory( (javax.jms.ConnectionFactory) instance );
		}
		return (ConnectionFactory) instance;
	}

	public static Destination asJakartaDestination(Object instance)  {
		if (instance instanceof javax.jms.Destination) {
			return Wrapper.fromJavaxDestination( (javax.jms.Destination) instance );
		}
		return (Destination) instance;
	}

	public static String destinationName(Destination destination) throws JMSException {
		if ( destination instanceof Queue ) {
			return ( (Queue) destination ).getQueueName();
		}
		else if ( destination instanceof Topic ) {
			return ( (Topic) destination ).getTopicName();
		}
		return null;
	}

	public static String destinationType(Destination destination) {
		if ( destination == null ) {
			return null;
		}
		return destination instanceof Queue ? "queue" : "topic";
	}

	public static String messageIdForLog(Message message) {
		try {
			return message.getJMSMessageID();
		}
		catch ( JMSException e ) {
			return "unknown";
		}
	}

	public static String destinationNameForLog(Destination destination) {
		try {
			return destinationName( destination );
		}
		catch ( JMSException e ) {
			return "unknown";
		}
	}
}
