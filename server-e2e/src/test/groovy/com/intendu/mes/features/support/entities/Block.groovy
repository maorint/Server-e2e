package com.intendu.mes.features.support.entities

import org.apache.commons.lang3.RandomStringUtils

import java.time.Instant


class Block {

    String internalId
    String sessionID
    String programEntryID
    String blockType
    Float startOffset
    Float endOffset
    Long startDate
    Float Difficulty
    Float ReactionDelayAverage
    Float SuccessRate
    CognitiveScore CognitiveScoreMapping = new CognitiveScore()

    class CognitiveScore {
        Float MinCognitiveScore = 20.0F
        Float MaxCognitiveScore = 80.0F
        Float MinDifficulty = 1.0F
        Float MaxDifficulty = 8.0F
    }

    Boolean isTest = true
    static String randomSessionID = null

    private static String[] blockTypes = ["SpatialMemoryResortBlock"] //, "InhibitionCategoryBlock", "SpatialMemoryBlock", "ShoppingBlock", "DividedAttentionBlock"]

    public static String[] getBlockTypes() { return blockTypes }

    static createBlock(String blockType, int timeoffset, boolean fixedSession) {
        if ((fixedSession && randomSessionID == null) || !fixedSession) {
            randomSessionID = getRandomHexValue()
        }
        return new Block(
                internalId:  UUID.randomUUID().toString(),
                sessionID: randomSessionID,
                programEntryID: getRandomHexValue(),
                blockType: blockType,
                startOffset: 0,
                endOffset: random.nextFloat()*60,
                startDate: Instant.now().toEpochMilli() + timeoffset,
                SuccessRate: random.nextFloat()*1,
                ReactionDelayAverage: random.nextFloat()*1,
                Difficulty: random.nextFloat()*1

        )
    }
    static createRandomBlock() {
        return createRandomBlock(Instant.now().toEpochMilli())
    }
    static createRandomBlock(long startDate) {

        return new Block(
                internalId:  UUID.randomUUID().toString(),
                sessionID: getRandomHexValue()  , //"ObjectId("+getRandomHexValue() + ")",
                programEntryID: getRandomHexValue(),
                blockType: getOneRandomElement(blockTypes),
                startOffset: 0,
                endOffset: random.nextFloat()*60,
                startDate: startDate,
                SuccessRate: random.nextFloat()*1,
                ReactionDelayAverage: random.nextFloat()*1,
                Difficulty: random.nextFloat()*1

        )
    }

    private static Random random = new Random();
    private static String getRandom(int bound) {
        return random.nextInt(bound).toString()
    }
    private static String getOneRandomElement(stringArray) {
        return stringArray[random.nextInt(stringArray.length)]
    }
    private static String getRandomHexValue() {
        String charset = (('a'..'f') + ('0'..'9')).join()
        return RandomStringUtils.random(24, charset.toCharArray())
    }

}
