package com.intendu.mes.features

import com.intendu.mes.features.support.entities.Block

this.metaClass.mixin(cucumber.api.groovy.Hooks)
this.metaClass.mixin(cucumber.api.groovy.EN)




Given(~/^I send one block$/) { ->
    blocks += createRandomBlocks(1)
    restClient.createNewBlocks_v2(blocks[0])
}

Given(~/^I send one block with startdate value$/) { ->
    blocks += Block.createRandomBlock(133101296000L)
    restClient.createNewBlocks_v1(blocks[0])
}
Given(~/^I use new api to send one block with startdate value$/) { ->
    blocks += Block.createRandomBlock(133101296000L)
    restClient.createNewBlocks_v2(blocks[0])
}


Given(~/^I send bulk of blocks$/) { ->
    blocks = createRandomBlocks(4)
    restClient.createBulkBlocks(blocks)
}

Given(~/^I send bulk of disordered blocks$/) { ->
    blocks = createDisorderedBlocks()
    restClient.createBulkBlocks(blocks)
}

def createDisorderedBlocks() {
    createDisorderedBlocks(false)
}

def createDisorderedBlocks(boolean fixedSession) {
    ArrayList<Block> newBlocks = new ArrayList<>()
    newBlocks.add(Block.createBlock("MultiTaskingBlock", 0, fixedSession))
    newBlocks.add(Block.createBlock("MultiTaskingBlock", -14000, fixedSession))
    newBlocks.add(Block.createBlock("MultiTaskingBlock", -4000, fixedSession))
    newBlocks.add(Block.createBlock("MultiTaskingBlock", -30000, fixedSession))

    return newBlocks
}
Given(~/^I send disordered blocks one by one$/) { ->
//    blocks = createDisorderedBlocks(true)
//    for (i=0; i<blocks.size(); i++) {
//        restClient.createNewBlocks_v2(blocks[i])
//    }
}


def createRandomBlocks(int amount) {
    ArrayList<Block> newBlocks = new ArrayList<>()
    amount.times {
        newBlocks.add(Block.createRandomBlock())
    }
    return newBlocks
}

Then(~/^(?:All |)?block(?: is|s are)? stored in server$/) { ->
    def storedBlocks = getStoredBlocks(blocks, PLAYER_ID, restClient)
    validateAllStored(blocks, storedBlocks, true)
    assert validateBlocksOrder(storedBlocks)
}

Then(~/^(?:All |)?block(?: is|s are)? stored and startdate is ignored$/) { ->
    def storedBlocks = getStoredBlocks(blocks, PLAYER_ID, restClient)
    validateAllStored(blocks, storedBlocks, false)
    assert validateBlocksOrder(storedBlocks)
}

Then(~/^All blocks are stored in right order in server$/) { ->
    def storedBlocks = getStoredBlocks(blocks, PLAYER_ID, restClient)
    validateAllStored(blocks, storedBlocks, true)
    assert validateBlocksOrder(storedBlocks)
}
Then(~/^All blocks are stored in right order in summary$/) { ->

    def storedBlocks = getBlocksPerTypeAndSession("58107eb08c29846d00577b2d", "5810c7148c298483009c155e", "MultiTaskingBlock", restClient)
//    def storedBlocks = getBlocksPerTypeAndSession(PLAYER_ID, blocks[0].sessionID, blocks[0].blockType, restClient)
    validateAllStored(blocks, storedBlocks, true)
    assert validateBlocksOrder(storedBlocks)
}
def validateAllStored(ArrayList<Block>  blocks, ArrayList<Object> storedBlocks, boolean expectEqualStartdate) {
    blocks.each {
        def blockFound = findBlock(it, storedBlocks)
        assert blockFound != null : "block id: " + it.internalId + " not found"
        assert blockFound.internalId == it.internalId
        assert expectEqualStartdate ? (blockFound.startDate == it.startDate) : (blockFound.startDate != it.startDate)
        println "found " + blockFound.internalId
    }
}
def validateBlocksOrder(ArrayList<Object> storedBlocks) {
    def t = 0
    for (i=0; i<storedBlocks.size(); i++) {
        if (t!=0 && t < storedBlocks[i].startDate) {
            print "block with time: " + t + " appears before block with time: " + storedBlocks[i].startDate
            return false
        }
        t = storedBlocks[i].startDate
    }
    return true
}
def getStoredBlocks(ArrayList<Block>  blocks, String playerId, restClient) {
    ArrayList<Object> storedBlocks = new ArrayList<>()
    Block.getBlockTypes().each {
        def res = getBlocksPerType(blocks, it, playerId, restClient)
        if (res != null)
            storedBlocks += res
    }
    return storedBlocks
}
def getBlocksPerType(ArrayList<Block>  blocks, String blockType, String playerId, restClient) {
    int amount = getBlocksAmountPerType(blocks, blockType)
    return (amount > 0) ?
            restClient.getLatestBlocks(playerId, blockType, amount) :
            null;
}

def getBlocksPerTypeAndSession(String playerId, String sessionId, String blockType, restClient) {
    return restClient.getBlockSummaryPerTypeAndSession(playerId, sessionId, blockType)
}


def getBlocksAmountPerType(ArrayList<Block>  blocks, String blockType) {
    return blocks.findAll{ it.blockType == blockType }.size()
}

Object findBlock(Block originalBlock, ArrayList<Object> blocks)  {
    return blocks.find { it.sessionID == originalBlock.sessionID && it.isTest == true }
}