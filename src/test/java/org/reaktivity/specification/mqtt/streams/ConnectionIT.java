/**
 * Copyright 2016-2019 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.specification.mqtt.streams;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.ScriptProperty;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;

public class ConnectionIT
{
    private final K3poRule k3po = new K3poRule()
            .addScriptRoot("scripts", "org/reaktivity/specification/mqtt");
    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
            "${scripts}/connect/successful/client",
            "${scripts}/connect/successful/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/disconnect/client",
        "${scripts}/disconnect/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectThenDisconnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/invalid.protocol.version/client",
        "${scripts}/connect/invalid.protocol.version/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectInvalidProtocolVersion() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/invalid.flags/client",
        "${scripts}/connect/invalid.flags/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectMalformedConnectPacket() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/successful.fragmented/client",
        "${scripts}/connect/successful.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldProcessFragmentedConnectPacket() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

}
