/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package senders;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.seedstack.seed.Bind;

@Bind
public class TestSender2 {

    @Inject
    @Named("connection1")
    private Connection connection;

    public void send(String stringMessage ) throws JMSException {
        //create connection
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // create destination
        Destination queue = session.createQueue("queue2");
        //create Message
        TextMessage message1 = session.createTextMessage();
        message1.setText(stringMessage);
        //add Message Properties
        message1.setJMSExpiration(1000);
        message1.setJMSReplyTo(queue);
        //get Message Producer
        MessageProducer producer = session.createProducer(queue);
        //send Message
        producer.send(message1);
        session.close();
    }

}