/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.index;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.search.Query;
import org.apache.maven.index.ArtifactInfo;
import org.apache.maven.index.FlatSearchRequest;
import org.apache.maven.index.FlatSearchResponse;
import org.apache.maven.index.NexusIndexer;

/**
 * @author Juven Xu http://issues.sonatype.org/browse/NEXUS-687
 */
public class Nexus687NexusIndexerTest
    extends AbstractNexusIndexerTest
{
    protected File repo = new File( getBasedir(), "src/test/nexus-687" );

    @Override
    protected void prepareNexusIndexer( NexusIndexer nexusIndexer )
        throws Exception
    {
        context =
            nexusIndexer.addIndexingContext( "nexus-687", "nexus-687", repo, indexDir, null, null, DEFAULT_CREATORS );
        nexusIndexer.scan( context );
    }

    public void testSearchFlat()
        throws Exception
    {
        Query q = nexusIndexer.constructQuery( ArtifactInfo.GROUP_ID, "xstream" );

        FlatSearchResponse response = nexusIndexer.searchFlat( new FlatSearchRequest( q ) );

        Collection<ArtifactInfo> r = response.getResults();

        assertEquals( 1, r.size() );

        List<ArtifactInfo> list = new ArrayList<ArtifactInfo>( r );

        assertEquals( 1, list.size() );

        ArtifactInfo ai = list.get( 0 );

        assertEquals( "xstream", ai.groupId );

        assertEquals( "xstream", ai.artifactId );

        assertEquals( "1.2.2", ai.version );

        assertNull( ai.packaging );
    }
}
