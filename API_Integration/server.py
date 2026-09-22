from fastapi import FastAPI
from pydantic import BaseModel
import random

app = FastAPI()
fired_shots = set()

class PlayerMove(BaseModel):
    player_shot: str

def generate_valid_shot():
    while True:
        row = random.choice("ABCDEFGHIJ")
        col = random.randint(1, 10)
        target = f"{row}{col}"

        if target not in fired_shots:
            fired_shots.add(target)
            return target

@app.post("/take-turn")
async def process_turn(move: PlayerMove):
    global fired_shots
    if move.player_shot == "START":
        fired_shots.clear()
    else:
        print(f"Incoming attack from Java at coordinate: {move.player_shot}")

    ai_move = generate_valid_shot()
    print(f"Computer decided to shoot at: {ai_move}")

    return {
        "shot_result": "PROCESSED",
        "ai_counter_attack": ai_move
    }