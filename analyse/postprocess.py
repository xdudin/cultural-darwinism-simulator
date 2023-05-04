import matplotlib.pyplot as plt
import numpy as np
import json
from scipy.ndimage import gaussian_filter


def plot(mode, extraction):
    with open(f'result/data_test/{mode}_60x60_{extraction}.json', 'r') as f:
        data = np.array(json.load(f)).transpose()

    data = gaussian_filter(data, sigma=1)

    fig, ax = plt.subplots()

    cmap = 'RdBu' if extraction == 'ac' else 'magma'
    vmin = -100 if extraction == 'ac' else 0
    im = ax.imshow(data, cmap=cmap, vmin=vmin, vmax=100)

    n_steps = 7

    x_values = np.linspace(1, 4, n_steps)
    labels = [f"{x:.1f}" for x in x_values]
    ticks = np.linspace(0, data.shape[1] - 1, n_steps).astype(int)

    ax.xaxis.tick_top()
    ax.xaxis.set_label_position('top')

    ax.set_xticks(ticks)
    ax.set_xticklabels(labels)

    ax.set_yticks(ticks)
    ax.set_yticklabels(labels)

    ax.figure.colorbar(im, ax=ax)

    ax.set_title(f'{mode.replace("_", " ").capitalize()} mode')
    ax.set_xlabel("Assimilation factor")
    ax.set_ylabel("Fertility factor")

    plt.tight_layout()
    plt.show()

    fig.savefig(f'result/png_submit/{mode}_{extraction}.png', dpi=300)


def main(mode):
    plot(mode, 'ac')
    plot(mode, 'aac')


if __name__ == '__main__':
    main('random')
    main('half')
    main('circle')
    main('quarter')
    main('four_lines')
