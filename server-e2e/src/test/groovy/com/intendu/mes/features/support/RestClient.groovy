package com.intendu.mes.features.support

import com.intendu.mes.features.support.entities.BlockResult

import com.intendu.mes.features.support.entities.UserInfo
import com.intendu.mes.features.support.rest.GetRestCall
import com.intendu.mes.features.support.rest.PostRestCall
import com.intendu.mes.features.support.rest.RestResponse
import groovy.json.JsonSlurper

class RestMesClient {
    class MesClientException extends RuntimeException {

        MesClientException(String message, RestResponse response) {
            super(message + " " + response.statusCode + " error: " + response.body)
        }
    }
    static final String LOGGEDIN_PATH = "v1/users/loggedin"
    static final String BULKBLOCKS_PATH = "v1/bulkBlocks/"
    static final String BLOCKS_PATH_V2 = "v2/blocks/"
    static final String BLOCKS_PATH_V1 = "v1/blocks/"
    static final String NEW_GAME_SESSION_PATH = "/v1/auth/signin/game/"
    static final String GET_BLOCKS = "v1/players/%s/blocks/%s/amount/%d"
    static final String GET_BLOCKS_SUMMARY = "v1/players/%s/sessions/%s/block/type/%s/summary"


    static final String SIGNIN_PATH = "/v1/auth/signin/credentials";
    static final String USERS_PATH = "/v1/users";
    static final String STATUS_PATH = "/v1/users/me/activation/status"

    String domain
    def restExecutor

    public RestMesClient(restExecutor) {
        this.restExecutor = restExecutor
    }

    UserInfo getLoggedinUser() {
        RestResponse response = restExecutor.execute(new GetRestCall(LOGGEDIN_PATH))
        throwIfNotValid(response, new MesClientException("Failed to signed in", response))
        return new JsonSlurper().parseText(response.body)

    }
    void createGameSession(String playerId) {
        RestResponse response = restExecutor.execute(new PostRestCall(NEW_GAME_SESSION_PATH + playerId))
        throwIfNotValid(response, new MesClientException("Failed to create game session", response))

        def json = new JsonSlurper().parseText(response.body)
        restExecutor.setToken(json.token.id)
    }
    ArrayList<BlockResult> getLatestBlocks(String playerId, String blockType, int limit) {
        RestResponse response = restExecutor.execute(new GetRestCall(String.format(GET_BLOCKS, playerId, blockType, limit)))
        throwIfNotValid(response, new MesClientException("Failed to get blocks", response))

        return new JsonSlurper().parseText(response.body)
    }
    ArrayList<BlockResult> getBlockSummaryPerTypeAndSession(String playerId, String sessionId, String blockType) {
        RestResponse response = restExecutor.execute(new GetRestCall(String.format(GET_BLOCKS_SUMMARY, playerId, sessionId, blockType)))
        throwIfNotValid(response, new MesClientException("Failed to get blocks summary", response))

        return new JsonSlurper().parseText(response.body)
    }
    void createNewBlocks_v1(block) {
        RestResponse response = restExecutor.execute(new PostRestCall(BLOCKS_PATH_V1, block))
        throwIfNotValid(response, new MesClientException("Failed to send blocks", response))
    }
    void createNewBlocks_v2(block) {
        def jsonBlock = [ "internalId": block.internalId, "sessionID":block.sessionID,"programEntryID":block.programEntryID,"blockType":block.blockType,"startOffset":block.startOffset,"endOffset":block.endOffset,"startDate":block.startDate,"Difficulty":block.Difficulty,"ReactionDelayAverage":block.ReactionDelayAverage,"SuccessRate":block.SuccessRate, "CognitiveScoreMapping" : ["MinCognitiveScore":block.CognitiveScoreMapping.MinCognitiveScore,"MaxCognitiveScore":block.CognitiveScoreMapping.MaxCognitiveScore, "MinDifficulty": block.CognitiveScoreMapping.MinDifficulty, "MaxDifficulty":block.CognitiveScoreMapping.MaxDifficulty]]

        RestResponse response = restExecutor.execute(new PostRestCall(BLOCKS_PATH_V2, jsonBlock))
        throwIfNotValid(response, new MesClientException("Failed to send blocks", response))
    }
    void createBulkBlocks(blocks) {
        RestResponse response = restExecutor.execute(new PostRestCall(BULKBLOCKS_PATH, blocks))
        throwIfNotValid(response, new MesClientException("Failed to send blocks", response))
    }

    UserInfo signin(organization, username, password) {
        RestResponse response = restExecutor.execute(new PostRestCall(SIGNIN_PATH), [nickname: username, orgname: organization, password: password])
        throwIfNotValid(response, new MesClientException("Failed to signed in", response))
        return new JsonSlurper().parseText(response.body)
    }


    private void throwIfNotValid(RestResponse response, RuntimeException e) {
        if ((response.statusCode / 100).toInteger() != 2) {
            throw e
        }
    }




    String getEmailStatus(){
        RestResponse response = restExecutor.execute(new GetRestCall(STATUS_PATH))
        throwIfNotValid(response, new MesClientException("Failed to get email status", response))
        def json = new JsonSlurper().parseText(response.body)
        return json.status;
    }
}




