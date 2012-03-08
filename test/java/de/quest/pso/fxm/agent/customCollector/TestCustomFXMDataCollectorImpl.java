/*
  Copyright 2012 Quest Software, Inc.
  ALL RIGHTS RESERVED.

  This software is the confidential and proprietary information of
  Quest Software Inc. ("Confidential Information").  You shall not
  disclose such Confidential Information and shall use it only in
  accordance with the terms of the license agreement you entered
  into with Quest Software Inc.

  QUEST SOFTWARE INC. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT
  THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
  INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
  NON-INFRINGEMENT. QUEST SOFTWARE SHALL NOT BE LIABLE FOR ANY
  DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
  OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */  

package de.quest.pso.fxm.agent.customCollector;

import com.quest.glue.api.services.ServiceFactory;
import com.quest.glue.tools.apimockimpl.MockServiceFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class contains the unit tests for the CustomFXMDataCollector agent. Although the agent 
 * is able to compile against the interfaces provided in glueapi.jar, at runtime it requires 
 * an implementation of those interfaces.  During normal operation these are
 * supplied by FglAM itself, but for unit testing mock implementations can be found 
 * in glueapimockimpl.jar.
 */
public class TestCustomFXMDataCollectorImpl {

private CustomFXMDataCollectorImpl mAgent;
private MockDataProvider mMockProvider;

@Before
public void initTest() throws ServiceFactory.ServiceNotAvailableException {
    MockServiceFactory serviceFactory = new MockServiceFactory();
    mMockProvider = new MockDataProvider(serviceFactory);
    mAgent = new CustomFXMDataCollectorImpl(serviceFactory, mMockProvider);
}

@After
public void cleanupTest() {
    mAgent = null;
    if ( mMockProvider != null ) {
        mMockProvider.cleanup();
    }
}

@Test
public void testStartDataCollection() throws Exception {
    mAgent.startDataCollection();
}

@Test
public void testStopDataCollection() throws Exception {
    mAgent.stopDataCollection();
}

/**
 * A mock data provider class that the agent can use during unit tests to (for example)
 * return a specific set of data
 * </p>
 * You could also use a mocking tool like <a href="http://mockito.org/">Mockito</a> to 
 * provide a mockup of the {@link CustomFXMDataCollectorDataProvider} interface.
 */
private static final class MockDataProvider implements CustomFXMDataCollectorDataProvider {

    private final ServiceFactory mFactory;

    /**
     * Creates a new mock data provider which will obtain all the services it needs
     * from the provided service factory.
     */
    private MockDataProvider(ServiceFactory factory) {
        mFactory = factory;
    }

    /**
     * Called after each unit test. Performs any cleanup of the data provider that
     * might be needed.
     */
    private void cleanup() {
        // examples: 
        //   close database connection
        //   delete temp files
    }
}

}